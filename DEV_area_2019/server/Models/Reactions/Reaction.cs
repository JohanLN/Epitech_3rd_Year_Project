using System.Collections.Generic;

namespace Area.Models
{
    public class Reaction
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public virtual void Run(RegisteredService service, Dictionary<string, string> data)
        {

        }
    }
}
