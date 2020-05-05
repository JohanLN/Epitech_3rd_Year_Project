import React from 'react'
import { StyleSheet, View, ToastAndroid, Image, Button} from 'react-native'
import { favoriteImage } from '../API/Api'
import Video from 'react-native-video'
import favorites from './FavoriteScreen'

class FavoriteItem extends React.Component {

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

    _deleteImage(fav) {
        favoriteImage(fav.id)
        ToastAndroid.show('Image deleted', ToastAndroid.SHORT)
    }

    render() {
        const fav = this.props.fav
        return(
            <View style={styles.main_container}>
                <View style={styles.content_container}>
                    {this._display(fav)}
                </View>
                <Button title="Delete image from favorite" onPress={() => this._deleteImage(fav)}></Button>
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

export default FavoriteItem