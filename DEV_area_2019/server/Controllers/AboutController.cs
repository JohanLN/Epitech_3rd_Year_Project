using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Area.Models;
using Area.Tools;
using System;

namespace Area.Controllers
{
    [Route("about.json")]
    [ApiController]
    public class AboutController : ControllerBase
    {
        private readonly Database<Service> _database = new Database<Service>("Services");

        public AboutController()
        {

        }

        // GET: about.json
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Service>>> GetAboutAsync()
        {
            TimeSpan time = DateTime.Now - new DateTime(2020, 1, 1);
            dynamic output = new
            {
                Client = new
                {
                    Host = "10.19.254.128:8080"
                },
                Server = new
                {
                    CurrentTime = (long)time.TotalMilliseconds,
                    Services = await _database.GetAll()
                }
            };
            
            return Ok(output);
        }
    }
}