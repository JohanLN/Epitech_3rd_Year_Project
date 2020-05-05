const galleryUrl = 'https://api.imgur.com/3/gallery/t/'
const sendFavoriteUrl = 'https://api.imgur.com/3/image/'
const favoriteUrl = 'https://api.imgur.com/3/account/'
const uploadUrl = 'https://api.imgur.com/3/image'
const myImages = 'https://api.imgur.com/3/account/me/images'
const delImage = 'https://api.imgur.com/3/account/'
const access = 'https://api.imgur.com/oauth2/token'

import Compuser from '../Component/Container'

export function searchImage (research) {
    return fetch(galleryUrl + research, {
        method: "GET",
        headers: {
        'Authorization': 'Client-ID ' + Compuser.Id_acount
        }
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}

export function favoriteImage (imageId) {
    return fetch(sendFavoriteUrl + imageId + '/favorite/', {
        method: "POST",
        headers: {
        Authorization: 'Bearer ' + Compuser.Access_token
        },
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}

export function findFavorite () {
    return fetch(favoriteUrl + Compuser.User_accout + "/favorites/", {
        method: "GET",
        headers: {
        Authorization: 'Bearer ' + Compuser.Access_token
        }
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}

export function imageUpload (imageUrl) {
    return fetch(uploadUrl, {
        method: "POST",
        body: imageUrl,
        headers: {
            Authorization: 'Client-ID' + Compuser.Id_acount
        }
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}

export function displayUploadedImages () {
    return fetch(myImages, {
        method: "GET",
        headers: {
            Authorization: 'Bearer ' + Compuser.Access_token
        }
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}

export function deleteUpImage (imageId) {
    return fetch(delImage + Compuser.User_accout + "/image/" + imageId, {
        method: "DELETE",
        headers: {
            Authorization: 'Bearer ' + Compuser.Access_token
        }
    })
    .then((response) => {
        return response.json()
    })
    .catch((error) => console.error(error))
}
