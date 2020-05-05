<template>
  <div id="FacebookAuth" >
    <h1>This is an FacebookAuth page</h1>
    <ManageView/>
    <facebook-login class="button"
      appId="588132325252269"
      @login="onLogin"
      @logout="onLogout"
      @get-initial-status="getUserData"
      @sdk-loaded="sdkLoaded">
    </facebook-login>
    <b-form-group class="Action" label="Receive a facebook message">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-1"
        v-model="selected"
        :options="options"
        name="flavour-1"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="Receive a facebook like">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-2"
        v-model="selected2"
        :options="options"
        name="flavour-2"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="Receive a facebook comment">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-3"
        v-model="selected3"
        :options="options"
        name="flavour-3"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="Receive a facebook publish">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-4"
        v-model="selected4"
        :options="options"
        name="flavour-4"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="Receive a facebook groupPost">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-5"
        v-model="selected5"
        :options="options"
        name="flavour-5"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <button class="btn btn-primary" v-on:click="fetchAction">Add Action</button>
  </div>
</template>

<script>
  import Config from "./../../components/config";
  import facebookLogin from 'facebook-login-vuejs'
  import ManageView from "./../ManageView"

    export default {
      name: "FacebookAuth",
      data() {
        return {
        selected: [],
        selected2: [],
        selected3: [],
        selected4: [],
        selected5: [],
        options: [
          { text: 'Send a Message', value: 'WillSendMessage' },
          { text: 'Post a like', value: 'WillLikePost' },
        ],
          isConnected: false,
          name: '',
          email: '',
          AccessToken: '',
          personalID: '',
          picture: '',
          FB: undefined,
          }
        },
        components: {
          ManageView,
          facebookLogin
        },
        created() {
          this.selected.push("GotMessage")
          this.selected2.push("GotLike")
          this.selected3.push("GotComment")
          this.selected4.push("GotNewPublish")
          this.selected5.push("GotNewGroupPost")
        },
        methods: {
          sdkLoaded(payload) {
            this.isConnected = payload.isConnected
            this.FB = payload.FB
            if (this.isConnected) 
              this.getUserData()
          },
          getUserData() {
          FB.api('/me', 'GET', { fields: 'id,name,email,picture,access_token'},
              user => {
                this.personalID = user.id;
                this.email = user.email;
                this.name = user.name;
                this.AccessToken = user.access_token;
              })
              FB.getLoginStatus(function(result) {
                if (result.status === 'connected') {
                  var accessToken = result.authResponse.accessToken
                  console.log("res.auth",result.authResponse)
                  console.log(accessToken)
                  var body = {"AccessToken":accessToken,"name":"Facebook"};
                  console.log("accessToken",accessToken);
                  var resultCallback = (result) => {
                    console.log(result);
                  };
                  var responseCallback = (res) => {
                    console.log(res);
                  }
                  console.log("body:  ", body)
                  Config.callServerApi("api/RegisteredServices", "POST", body, resultCallback, responseCallback);
                }
              });
          },
          onLogin() {
            this.isConnected = true
            this.getUserData()
          },
          onLogout() {
            this.isConnected = false;
          },
          fetchAction() {
            if (this.selected[1] !== null) {
              this.selected.shift()
              var data = {"name":"GotMessage","Description":"blablabla","Reaction":{"name":this.selected,"description":"","actions":null,"params":null}}
            }
            if (this.selected2[1] !== null) {
              this.selected2.shift()
              var data = {"name":"GotLike","Description":"blablabla","Reaction":{"name":this.selected2,"description":"","actions":null,"params":null}}
            }
            if (this.selected3[1] !== null) {
              this.selected3.shift()
              var data = {"name":"GotComment","Description":"blablabla","Reaction":{"name":this.selected3,"description":"","actions":null,"params":null}}
            }
            if (this.selected4[1] !== null) {
              this.selected4.shift()
              var data = {"name":"GotNewPublish","Description":"blablabla","Reaction":{"name":this.selected4,"description":"","actions":null,"params":null}}
            }
            if (this.selected5[1] !== null) {
              this.selected5.shift()
              var data = {"name":"GotNewGroupPost","Description":"blablabla","Reaction":{"name":this.selected5,"description":"","actions":null,"params":null}}
            }
            var responseCallback = (res) => {
                console.log(res);
            };
            var resultCallback = (result) => {
                console.log("connect", result);
            };
            Config.callServerApi(`api/RegisteredActions/`, "POST", data, resultCallback, responseCallback);
          }
        }
    }
</script>

<style>
.button {
  margin: auto;
}
.Action  {
  margin-top: 120px;
}

</style>