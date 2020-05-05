using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Area.Models;
using Area.Tools;
using Microsoft.Extensions.Logging;

namespace Area.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly Database<User> _database = new Database<User>("Users");
        private readonly ILogger<UsersController> _logger;
        public UsersController(ILogger<UsersController> logger)
        {
            _logger = logger;
        }

        private void FillSession(User user)
        {
            HttpContext.Session.SetObject("Id", user.Id);
            HttpContext.Session.SetString("Name", user.Name);
            HttpContext.Session.SetString("Email", user.Email);
            HttpContext.Session.SetObject("RegisteredServices", user.RegisteredServices);
            HttpContext.Session.SetObject("RegisteredActions", user.RegisteredActions);
        }
        // GET: api/Users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsersAsync()
        {
            return await _database.GetAll();
        }

        // GET: api/Users/5
        [ActionName("GetUserAsync")]
        [HttpGet("{id}")]
        public async Task<ActionResult<User>> GetUserAsync(long id)
        {
            User output = await _database.Get(id);

            if (output == null)
                return NotFound();
            return Ok(output);
        }
        
        // GET: api/Users/logout
        [HttpGet("logout")]
        public ActionResult<User> LogoutUser()
        {
            HttpContext.Session.Clear();
            return NoContent();
        }

        [HttpPost("login")]
        public async Task<ActionResult<User>> LoginUserAsync([FromBody] User user)
        {
            User output = await _database.Get(user.Name);

            if (output == null || output.Password != Models.User.HashPassword(user.Password))
            {
                return NotFound();
            }
            FillSession(output);
            return CreatedAtAction(nameof(GetUserAsync), new { id = output.Id }, output);
        }

        // PUT: api/Users/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPut("{id}")]
        public async Task<ActionResult<User>> PutUserAsync(long id, User user)
        {
            User userData = await _database.Get(id);

            if (userData == null)
                return (NotFound());
            user.Id = id;
            user.RegisteredServices = userData.RegisteredServices;
            if (string.IsNullOrEmpty(user.Name))
                user.Name = userData.Name;
            if (string.IsNullOrEmpty(user.Email))
                user.Email = userData.Email;
            if (!string.IsNullOrEmpty(user.Password))
                user.Password = Models.User.HashPassword(user.Password);
            else
                user.Password = userData.Password;
            FillSession(user);
            return await _database.Put(id, user);
        }

        // POST: api/Users
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPost]
        public async Task<ActionResult<User>> PostUserAsync([FromBody] User user)
        {
            User output;
            User checker = await _database.Get(user.Name);

            if (checker != null || user.Name == null || user.Password == null)
                return BadRequest();
            user.Password = Models.User.HashPassword(user.Password);
            output = await _database.Post(user.Id, user);
            if (output == null)
                return BadRequest();
            return CreatedAtAction(nameof(GetUserAsync), new { id = output.Id }, output);
        }

        // DELETE: api/Users/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<User>> DeleteUserAsync(long id)
        {
            await _database.Delete(id);

            return NoContent();
        }
    }
}
