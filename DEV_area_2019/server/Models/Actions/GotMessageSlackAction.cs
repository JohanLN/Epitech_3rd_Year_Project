using Area.Models.Services;
using System.Collections.Generic;

namespace Area.Models.Actions
{
    public class GotMessageSlackAction : Action
    {
        private List<string> _oldMessages = null;
        private List<string> _messages = null;
        public GotMessageSlackAction(SlackService slackService) : base(slackService)
        {

        }

        public override async void Refresh()
        {
            SlackService service = (SlackService)_service;

            _oldMessages = _messages;
            if (_messages == null)
                _messages = await service.GetMessagesAsync();
            if (_oldMessages == null)
                return;
            if (_oldMessages.Count != _messages.Count)
                OnTriggered();
        }
    }
}
