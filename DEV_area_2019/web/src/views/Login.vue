<template>
      <div>
        <h1 class="title">Area</h1>
        <b-form  @submit.prevent="login" v-if="show">
            <b-form-group id="input-group-1" label-for="name" align="center">
                <b-form-input type="text" id="name" v-model="form.name" required placeholder="Name"></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-3" label-for="pwd" align="center">
                <b-form-input type="password" id="password" v-model="form.password" required placeholder="Password"></b-form-input>
            </b-form-group>
            <p class="text">Not a member? <b-link  href="/#/register">Sign up now</b-link></p>
            <br/>
            <button class="btn btn-black" pill type="submit">Login</button>
        </b-form>
    </div>
</template>

<script>
import Config from "../components/config";
export default {
    name: 'Login',
    data() {
        return {
            form: {
                name: '',
                password: ''
            },
            show: true,
      }
    },
    methods: {
        login() {
            var name = this.form.name
            var password = this.form.password
            console.log(name, password)
            var responseCallback = (res) =>
            {
                console.log(res);
                if (res.status === 201) {
                    this.$store.commit("setAuthentication", true);
                    this.$router.replace({ name: "services" });
                } else
                    alert("The username and/or password is incorrect")
            };
            var resultCallback = (result) =>
            {
                console.log("connect", result);
            };
            Config.callServerApi(`api/Users/login`, "POST", {password: this.form.password, Name: this.form.name}, resultCallback, responseCallback);
        }, 
        pressed(){
            login()
        },
    }
}
</script>

<style lang="scss" scoped>
div {
  color: inherit;
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
.btn-black{
    background-color:#033F63;
    color: #FFF;
    margin-top: 30px;
    width: 200px;
    height: 70px;
    font-size: 150%;
    border-radius: 50px;
}
.title {
    margin-bottom: 50px;
    margin-top: 100px;
    color: #033F63;
}
.text {
    margin-top: 50px;
}
.error {
  color: red;
}
</style>