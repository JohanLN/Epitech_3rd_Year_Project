import React from 'react'
import { StyleSheet, View, TextInput, Button, ToastAndroid } from 'react-native'
import { imageUpload } from '../API/Api'

class Upload extends React.Component {

    constructor(props) {
        super(props)
        this.ImageURL = ""
      }    

      _ImageURL(text) {
          this.ImageURL = text
      }

      _upImage() {
          imageUpload(this.ImageURL)
          ToastAndroid.show('Image uploaded', ToastAndroid.SHORT)
      }

    render() {
        return (
            <View style={styles.main_container}>
                <TextInput
                    style={styles.textinput}
                    placeholder='Enter an image URL'
                    onChangeText={(text) => this._ImageURL(text)}
                    onSubmitEditing={() => imageUpload(this.ImageURL)}>
                </TextInput>
                <Button title="Upload"
                        onPress={() => this._upImage()}></Button>
            </View>
        )
    }
}

const styles = StyleSheet.create({

    main_container: {
        height: 600,
        flexDirection: 'column',
    },
    textinput: {
        marginTop: 200,
        marginLeft: 5,
        marginRight: 5,
        height: 50,
        borderColor: '#000000',
        borderWidth: 1,
        paddingLeft: 5
      },
})

export default Upload