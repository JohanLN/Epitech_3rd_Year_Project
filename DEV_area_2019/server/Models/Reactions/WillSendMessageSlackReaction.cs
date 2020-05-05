using Area.Models.Services;
using System.Collections.Generic;

namespace Area.Models.Reactions
{
    public class WillSendMessageSlackReaction : Reaction
    {
        public override async void Run(RegisteredService service, Dictionary<string, string> data)
        {
            SlackService slackService = (SlackService)service;
            List<string> channelIds = await slackService.GetChannels();

            data["content"] ??= "(null)";
            if (channelIds.Count > 0)
            {
                data["target"] ??= channelIds[0];
            }
            if (data["target"] != null)
            {
                await slackService.SendMessageAsync(data["target"], data["content"]);
            }
        }
    }
}
