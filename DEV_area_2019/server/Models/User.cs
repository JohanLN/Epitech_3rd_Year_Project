using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using System;
using System.Collections.Generic;

namespace Area.Models
{
    public class User : Model
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public List<RegisteredService> RegisteredServices { get; set; }
        public List<Action> RegisteredActions { get; set; }

        public static string HashPassword(string password)
        {
            const short BYTES_REQUESTED = 16;
            byte[] salt = new byte[BYTES_REQUESTED];
            byte[] hashedBytes = KeyDerivation.Pbkdf2(password, salt, KeyDerivationPrf.HMACSHA256, 100, BYTES_REQUESTED);

            return (Convert.ToBase64String(hashedBytes));
        }
    }
}