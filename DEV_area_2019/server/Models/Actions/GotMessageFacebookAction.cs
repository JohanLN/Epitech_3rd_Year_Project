using Area.Models.Services;

namespace Area.Models
{
    public class GotMessageFacebookAction : Action
    {
        public GotMessageFacebookAction(FacebookService service) : base(service)
        {

        }
        public override void Refresh()
        {
            string url;

            url = _service.BaseUrl + "/me/inbox?access_token=" + _service.AccessToken;
        }
    }
}
