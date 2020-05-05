using System;

namespace Area.Models
{
    abstract public class Model
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public Model()
        {
            DateTime baseTime = new DateTime(2020, 1, 1);
            TimeSpan diff = DateTime.Now - baseTime;
            
            Id = (long)diff.TotalMilliseconds;
        }
    }
}
