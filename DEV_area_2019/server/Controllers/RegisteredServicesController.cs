using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;
using Area.Models;
using Area.Tools;
using Microsoft.Extensions.Logging;

namespace Area.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RegisteredServicesController : ControllerBase
    {
        private readonly Database<User> _database = new Database<User>("Users");
        private readonly Database<Service> _services = new Database<Service>("Services");
        private readonly ILogger<RegisteredServicesController> _log;
        public RegisteredServicesController(ILogger<RegisteredServicesController> logger)
        {
            _log = logger;
        }

        private static RegisteredService IsServiceRegistered(User user, string serviceName)
        {
            foreach (var service in user.RegisteredServices)
            {
                if (service.Name == serviceName)
                    return (service);
            }
            return (null);
        }

        [ActionName("GetRegisteredServiceAsync")]
        [HttpGet]
        public ActionResult<List<RegisteredService>> GetRegisteredServiceAsync()
        {
            var output = HttpContext.Session.GetObject<List<RegisteredService>>("RegisteredServices");

            return Ok(output);
        }

        [HttpPost]
        public async Task<ActionResult<List<RegisteredService>>> RegisterServiceAsync([FromBody] RegisteredService newService)
        {
            long id = HttpContext.Session.GetObject<long>("Id");
            User user = await _database.Get(id);
            Service service = await _services.Get(newService.Name);
            RegisteredService registeredService;

            if (service == null || user == null)
                return NotFound();
            if (newService.AccessToken == null)
                return BadRequest();
            user.RegisteredServices ??= new List<RegisteredService>();
            registeredService = IsServiceRegistered(user, newService.Name);
            if (registeredService == null)
            {
                registeredService = new RegisteredService(newService)
                {
                    Actions = service.Actions,
                    Reactions = service.Reactions
                };
                user.RegisteredServices.Add(registeredService);
            }
            else
            {
                registeredService.AccessToken = newService.AccessToken;
                registeredService.RefreshToken = newService.RefreshToken;
            }
            HttpContext.Session.SetObject("RegisteredServices", user.RegisteredServices);
            await _database.Put(id, user);
            return CreatedAtAction(nameof(GetRegisteredServiceAsync), user.RegisteredServices);
        }

        [HttpDelete("{serviceName}")]
        public async Task<ActionResult<RegisteredService>> DeleteUserAsync(string serviceName)
        {
            long id = HttpContext.Session.GetObject<long>("Id");
            User user = await _database.Get(id);
            RegisteredService registeredService;

            if (user == null)
                return NotFound();
            registeredService = IsServiceRegistered(user, serviceName);
            if (registeredService == null)
                return NotFound();
            user.RegisteredServices.Remove(registeredService);
            HttpContext.Session.SetObject("RegisteredServices", user.RegisteredServices);
            await _database.Put(id, user);
            return registeredService;
        }
    }
}
