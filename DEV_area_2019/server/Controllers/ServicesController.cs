using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Area.Models;
using Area.Tools;

namespace Area.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ServicesController : ControllerBase
    {
        private readonly Database<Service> _database = new Database<Service>("Services");

        public ServicesController()
        {

        }

        // GET: api/Services
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Service>>> GetServicesAsync()
        {
            return Ok(await _database.GetAll());
        }

        // GET: api/Services/id
        [HttpGet("{id}")]
        public async Task<ActionResult<Service>> GetServiceAsync(long id)
        {
            Service output = await _database.Get(id);

            if (output == null)
            {
                return NotFound();
            }
            return Ok(output);
        }
    }
}