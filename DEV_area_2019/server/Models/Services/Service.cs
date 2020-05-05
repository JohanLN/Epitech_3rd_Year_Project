using System.Collections.Generic;

namespace Area.Models
{
    public class Service : Model
    {
        public string BaseUrl;
        public List<Action> Actions { get; set; }
        public List<Reaction> Reactions { get; set; }
    }
}
