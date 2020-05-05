using System.Threading;
using System.Threading.Tasks;

namespace Area.Scheduler
{
    public interface IScheduledTask
    {
        public string Schedule { get; }
        Task ExecuteAsync(CancellationToken cancellationToken);
    }
}