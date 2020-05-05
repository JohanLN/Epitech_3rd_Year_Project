using System.Collections.Generic;

namespace Area.Models
{
    public class Action : Model
    {
        public Reaction Reaction { get;  set; }
        public string Description { get; set; }

        protected readonly RegisteredService _service;

        public Action()
        {

        }
        public Action(RegisteredService service)
        {
            _service = service;
        }
        
        protected void OnTriggered()
        {
            Reaction.Run(_service, GetData());
        }
        public virtual void Refresh()
        {

        }
        protected virtual Dictionary<string, string> GetData()
        {
            return (null);
        }
    }
}
