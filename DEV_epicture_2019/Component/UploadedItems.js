import React from 'react'
import { StyleSheet, View, Text, Image, Button, FlatList, ToastAndroid} from 'react-native'
import Video from 'react-native-video'
import { deleteUpImage } from '../API/Api'

class UploadedItems extends React.Component {

    _display (item) {
        if (item.type == "video/mp4" || item.type == "image/gif") {
            return (
                <Video style={styles.image}
                source={{uri: item.link}}>
                </Video>
            )
        }
        else {
         return (
                <Image style={styles.image}
                source={{uri: item.link}}>
                </Image>
            )
        }
    }

    _delImage(up) {
        deleteUpImage(up.id).then(data => {
            if (!data.success) {
                ToastAndroid.show('Connect you first', ToastAndroid.SHORT)
            }
            else {
                ToastAndroid.show('Image deleted', ToastAndroid.SHORT)
            }
        })
    }

    render() {
        const up = this.props.up
        return(
            <View style={styles.main_container}>
                <View style={styles.content_container}>
                    {this._display(up)}
                </View>
                <Button title="Delete image"
                        onPress={() => this._delImage(up)}></Button>
            </View>
        )
    }
}

const styles = StyleSheet.create({

    main_container: {
        height: 320,
        flexDirection: 'column',
    },
    image_title: {
        fontWeight: 'bold',
        fontSize: 16,
        height: 20,
        flexWrap: 'wrap',
        paddingRight: 5,
        textAlign: 'center',
    },
    content_container: {
        flex: 1,
        margin: 5
    },
    image: {
        height: 250,
        margin: 5,
    },
})

export default UploadedItems