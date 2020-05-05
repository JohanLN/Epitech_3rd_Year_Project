import React from 'react'
import { StyleSheet, View, Text, Image, Button, FlatList, ToastAndroid} from 'react-native'
import { favoriteImage } from '../API/Api'
import Video from 'react-native-video'

class ImageItem extends React.Component {

    _display (item) {
        if (item.type == "video/mp4") {
            return (
                <Video style={styles.image}
                source={{uri: item.link}}>
                </Video>
            )
        }
        else if (item.type == "image/gif") {
            <Image style={styles.image}
            source={require("../assets/error-t.jpg")}>
            </Image>
        }
        else {
            return (
                <Image style={styles.image}
                source={{uri: item.link}}>
                </Image>
            )
        }
    }

    _favImage(id) {
        favoriteImage(id)
        ToastAndroid.show('Image add to favorite', ToastAndroid.SHORT)
    }

    render() {
        var id
        const img = this.props.img
        return (
            <View style={styles.main_container}>
                <Text style={styles.image_title}>{img.title}</Text>
                <View style={styles.content_container}>
                    <FlatList
                        data={img.images}
                        keyExtractor={(item) => id = item.id}
                        renderItem={({item}) =>
                        this._display(item)}>
                    </FlatList>
                    <Button title="Add to favorite"onPress={() => this._favImage(id)}></Button>
                </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({

    main_container: {
        flex: 1,
        flexDirection: 'column',
    },
    image: {
        height: 250,
        margin: 5,
    },
    video: {
        position: 'absolute',
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
      },
    content_container: {
        flex: 1,
        margin: 5
    },
    image_title: {
        fontWeight: 'bold',
        fontSize: 16,
        height: 20,
        flexWrap: 'wrap',
        paddingRight: 5,
        textAlign: 'center',
    },
    button_favorite: {
        alignItems: 'center',
        height: 80,
    },
    favorite_image: {
        height: 40,
        width: 40,
    }
})

export default ImageItem