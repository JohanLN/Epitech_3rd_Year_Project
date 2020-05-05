<template>
  <div>
    <h1 class="title">Area</h1>
    <b-form @submit.prevent="pressed" v-if="show">
            <b-form-group id="input-group-1" label-for="name" align="center">
                <b-form-input type="text" id="name" v-model="form.name" required placeholder="Name"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-2" label-for="email" align="center">
                <b-form-input type="email" id="email" v-model="form.email" required placeholder="Email"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-3" label-for="password" align="center">
                <b-form-input type="password" id="password" v-model="form.password" required placeholder="Password"></b-form-input>
            </b-form-group>
            <p class="text">Already have an account? <b-link  href="/#/login">Sign in</b-link></p>
            <br/>
            <button class="btn btn-black" pill type="submit">Register</button>
        </b-form>
    <div class="error" v-if="error">{{error.message}}</div>
  </div>
</template>

<script>
import Config from "../components/config";

export default {
  name: 'Register',
  data() {
    return {
      form: {
          name: '',
          email: '',
          password: ''
        },
        show: true,
        error: false
      }
    },
    methods: {
    pressed() {
      var name = this.form.name
      var mail = this.form.email
      var password = this.form.password
      var data = {"name":name,"email":mail,"password":password};
      
      var resultCallback = (result) => {
        console.log("result", result)
        this.$router.replace({name: "login"})
      };
      var responseCallback = (res) => {
        console.log("response",res);
      };
      Config.callServerApi("api/users", "POST", data, resultCallback, responseCallback);
       /*var raw = JSON.stringify({"name":name,"email":mail,"password":password})

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };
        fetch(Config.IP + "/api/users", requestOptions)
            .then(response => {
                return response.text()
                console.log("reponse",response)
            })
            .then(result => { result
                console.log("result", result)
                this.$router.replace({name: "login"})
            })
            .catch(err => this.error = err);*/
        }
    }
    
};
</script>

<style lang="scss" scoped>
.error {
  color: red;
  font-size: 18px;
}

input {
  width: 400px;
  padding: 30px;
  margin: 20px;
  font-size: 21px;
  background-color: #EDEEF0;
  border-color: #033F63;
}

button {
  margin-top: 30px;
  width: 200px;
  height: 70px;
  font-size: 150%;
}
.title {
    margin-bottom: 50px;
    margin-top: 100px;
    color: #033F63;
}
.text {
    margin-top: 50px;
}
.btn-black{
    background-color:#033F63;
    color: #FFF;
    margin-top: 10px;
    width: 200px;
    height: 70px;
    font-size: 150%;
    border-radius: 50px;
}

</style>