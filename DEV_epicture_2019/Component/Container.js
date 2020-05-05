import {AsyncStorage} from "react-native"

class Compuser {
    static Id_acount = "";
    static User_accout = "";
    static Access_token = "";

    static async get() {
        this.InfoURL = JSON.parse(await AsyncStorage.getItem('user').catch());
        return this.InfoURL;
    }

    static get_Id() {
        return (this.Id_acount);
    }
    static get_User_account () {
        return (this.User_accout);
    }
    static get_Access() {
        return (this.Access_token);
    }

    static async set(user) {
        if (this.Id_acount.length == 0) {
            this.Id_acount = user[0][1];
            console.log("SET ID: ")
            console.log(this.Id_acount)
            this.User_accout = user[1][1];
            console.log("SET USER: ")
            console.log(this.User_accout)
            this.Access_token = user[5][1];
            console.log("SET TOKEN: ")
            console.log(this.Access_token)
        }
    }
}

export default Compuser;