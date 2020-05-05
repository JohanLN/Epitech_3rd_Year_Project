using Microsoft.AspNetCore.Mvc;
using Area.Models;
using Area.Tools;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;

namespace Area.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ActionsController : ControllerBase
    {
        private readonly Database<User> _database = new Database<User>("Users");
        private readonly ILogger<ActionsController> _logger;
        public ActionsController(ILogger<ActionsController> logger)
        {
            _logger = logger;
        }

        private bool CheckRegistration(List<RegisteredService> registeredServices, string name)
        {
            if (registeredServices == null || name == null)
                return (false);
            foreach (var rs in registeredServices)
            {
                if (rs.Actions == null)
                    continue;
                foreach (var action in rs.Actions)
                {
                    _logger.LogWarning(action.Name);
                    if (action.Name == name)
                        return (true);
                }
            }
            return (false);
        }

        private Reaction GetReaction(List<RegisteredService> registeredServices, string reaction)
        {
            if (registeredServices == null || reaction == null)
                return (null);
            foreach (var rs in registeredServices)
            {
                if (rs.Reactions == null)
                    continue;
                foreach (var reactions in rs.Reactions)
                {
                    _logger.LogWarning(reactions.Name);
                    if (reactions.Name == reaction)
                        return (reactions);
                }
            }
            return (null);
        }

        // GET: api/Actions/5
        [ActionName("GetActionsAsync")]
        [HttpGet]
        public ActionResult<List<Action>> GetActionsAsync()
        {
            var output = HttpContext.Session.GetObject<List<Action>>("RegisteredActions");

            return Ok(output);
        }

        [HttpPost]
        public async Task<ActionResult<List<Action>>> RegisterActionAsync(Action action)
        {
            long id = HttpContext.Session.GetObject<long>("Id");
            User user = await _database.Get(id);

            if (user == null)
                return NotFound();
            if (action.Name == null || CheckRegistration(user.RegisteredServices, action.Name))
                return BadRequest();
            if (user.RegisteredActions == null)
                user.RegisteredActions = new List<Action>();
            action.Reaction = GetReaction(user.RegisteredServices, action.Reaction.Name);
            user.RegisteredActions.Add(action);
            HttpContext.Session.SetObject("RegisteredActions", user.RegisteredActions);
            await _database.Put(id, user);
            return CreatedAtAction(nameof(GetActionsAsync), user.RegisteredActions);
        }

        [HttpDelete("{actionId}")]
        public async Task<ActionResult<Action>> DeleteRegisteredActionAsync(long actionId)
        {
            long id = HttpContext.Session.GetObject<long>("Id");
            User user = await _database.Get(id);
            Action action = null;

            if (user == null)
                return NotFound();
            foreach (var a in user.RegisteredActions)
            {
                if (a.Id == actionId)
                {
                    action = a;
                    break;
                }
            }
            if (action == null)
            {
                return NotFound();
            }
            user.RegisteredActions.Remove(action);
            HttpContext.Session.SetObject("RegisteredActions", user.RegisteredActions);
            await _database.Put(id, user);
            return action;
        }
    }
}
