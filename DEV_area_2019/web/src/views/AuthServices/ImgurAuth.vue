<template>
  <div>
    <h1>Social Login with</h1>
    <ManageView/>
    <OAuth provider="imgur"/>
    <b-form-group class="Action" label="New image Imgur">
      <b-form-checkbox-group size="lg"
        id="checkbox-group-1"
        v-model="selected"
        :options="options"
        name="flavour-1"
        buttons
        button-variant="primary"
      ></b-form-checkbox-group>
    </b-form-group>
    <b-form-group class="Action" label="New like Imgur">
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
  name: "ImgurAuth",
  data() {
    return {
      selected: [],
      selected2: [],
      options: [
        { text: 'Send a email', value: 'Send a email' },
        { text: 'Send message twitter', value: 'Send message twitter' },
      ],
      options2: [
        { text: 'Send a email', value: 'Send a email' },
        { text: 'Send message twitter', value: 'Send message twitter' },
      ],
    }
  },
  components: {
    ManageView,
    OAuth
  },
  created() {
    this.selected.push("GotMessage")
    this.selected2.push("LIKE")
  },
  methods: {
    fetchAction() {
      if (this.selected[1] !== null) {
        this.selected.shift()
        var data = {"name":"GotMessage","Description":"blablabla","Reaction":{"name":this.selected[0],"description":"","actions":null,"params":null}}
      }
      if (this.selected2[1] !== null) {
        this.selected2.shift()
        var data = {"name":"GotMessage","Description":"blablabla","Reaction":{"name":WillSendMessage,"description":"Send a message to someone","actions":null,"params":{
			"target":"Alexandre Cochet",
			"content":"salut les putes"
		}}}
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
};
</script>

<style>
</style>