<template>
  <div>
    <h1>Social Login with Slack</h1>
    <ManageView/>
    <OAuth provider="slack"/>
    <b-form-group class="Action" label="Receive a Slack message">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-1"
        v-model="selected"
        :options="options"
        name="flavour-1"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="Receive a Slack like">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-2"
        v-model="selected2"
        :options="options2"
        name="flavour-2"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <button class="btn btn-primary" v-on:click="fetchAction">Add Action</button>
  </div>
</template>

<script>
import OAuth from "../../components/OAuth"
import ManageView from "./../ManageView"
import Config from "./../../components/config";

export default {
  name: "SlackAuth",
  data() {
    return {
      selected: [],
      selected2: [],
      options: [
        { text: 'Send a message', value: 'WillSendMessage' },
        { text: 'Send message twitter', value: 'Send message twitter' },
      ],
      options2: [
        { text: 'Send a message', value: 'WillSendMessage' },
        { text: 'Send message twitter', value: 'Send message twitter' },
      ],
    }
  },
  components: {
    ManageView,
    OAuth
  },
  methods: {
    fetchAction() {
      if (this.selected[1] !== null) {
        var data = {"name":"GotMessage", "Reaction":{"name":"WillSendMessage", "params":{
          "target":"CU9K1QQ8H",
          "content":"salut les putes"}}}
      }
      if (this.selected2[1] !== null) {
        var data = 
          {"name":"GotMessage", 
            "Reaction":{
              "name":"WillSendMessage", 
              "params":{
                "target":"CU9K1QQ8H",
                "content":"salut les putes"
              }
            }
        }    
    }
      var responseCallback = (res) => {
          console.log(res);
      };
      var resultCallback = (result) => {
          console.log("connect", result);
      };
      Config.callServerApi(`api/Actions/`, "POST", data, resultCallback, responseCallback);
    }
  }
};
</script>

<style>
.btn-social {
    position: relative;
    padding-left: 44px;
    text-align: left;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.btn-block {
    display: block;
    width: 100%;
    padding-right: 0;
    padding-left: 0;
}
.btn-lg {
    padding: 14px 16px;
    font-size: 18px;
    line-height: 1.33;
    border-radius: 6px;
}
.btn {
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
.Action  {
  margin-top: 120px;
}
</style>