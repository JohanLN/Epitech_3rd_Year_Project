<template>
  <a @click="Auth();" :class="class2">
    <link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel = "stylesheet" href = "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../assets/bootstrap-social.css">
    <span :class="class1"></span>
    Sign in with {{ provider }}
  </a>
</template>

<script>
import { OAuth } from "oauthio-web";
import Config from "./config"
export default {
  props: ["provider"],
  data() {
    return {
      class1: "fa fa-" + this.provider,
      class2: "btns btn-blocks btn-socials btn-lgs btn-" + this.provider,
      access_token: ""
    };
  },
  created() {
    OAuth.initialize("KYeI9XgOBJEaNr02WaNwJ12tFEE");
  },
  methods: {
    Auth() {
      OAuth.popup(this.provider)
        .done(res => {
          console.log("connect social",res.access_token)
          console.log("connect social",res)
          
          if (this.provider === "twitter") {
            var accessToken = res.oauth_token
            var accessTokenSecret = res.oauth_token_secret
            var body = {"AccessToken":accessToken,"name":this.provider}
          }
          else if (this.provider === "slack") {
            var accessToken = res.access_token
            var body = {"AccessToken":accessToken,"name":"Slack"}
          }
          else if (this.provider === "imgur") {
            var accessToken = res.access_token
            var body = {"AccessToken":accessToken,"name":"Imgur"}
          } else {
            var accessToken = res.access_token
            var body = {"AccessToken":accessToken,"name":this.provider}
          }
          
          
          console.log("accessToken",accessToken)
          console.log("provider",this.provider)
          var resultCallback = (result) => {
            console.log(result);
          };
          var responseCallback = (res) => {
            console.log(res);
          }
          Config.callServerApi("api/RegisteredServices", "POST", body, resultCallback, responseCallback);
        })
        .fail(err => {
          console.log(err); //todo when the OAuth flow failed
        });
    }
  }
};
</script>
<style>
.btn-socials {
    position: relative;
    padding-left: 44px;
    text-align: left;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
};
.btn-blocks {
    display: block;
    width: 100%;
    padding-right: 0;
    padding-left: 0;
};
.btn-lgs {
    padding: 14px 16px;
    font-size: 18px;
    line-height: 1.33;
    border-radius: 6px;
};
.btns {
    display: inline-block;
    padding: 8px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: normal;
    line-height: 1.428571429;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    -o-user-select: none;
    user-select: none;
}
</style>
