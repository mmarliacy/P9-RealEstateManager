# RealEstateManager - Introduce your real estate
##### *This README.md will allow you to know the features of the project, and will permit you to make changes later if necessary.*

### Summary
   1. [General information](#general-information);
   2. [Project screenshots](#project-photos);
   3. [Dependencies and technologies used](#technologies-used);
   4. [Installation of the project](#how-to-install);
   5. [Features developed](#features-developed);
   6. [Project authors and contributors](#authors-and-contributors);
   7. [Android Studio Version](#version-android-studio).
   8. [How can you improve that project](#how-to-improve).

#### #General informations
***
*Real estate manager is an application that allows real estate agents to list properties acquired on behalf of a real estate company. 
This also allows them to have a visual of the property to present to the customer, including photos, information about the property, or even to know if the property has already been sold.*
Among other things, the application had the following features:
* Add/Remove/Update Property
* Filter the properties present in the list according to the criteria developed (Number of photos, price, size of the property...) on the basis of SQL queries.
* The real estate agent can find a map in the description of the property approximately locating it, thanks to the integration of Maps Static API.
* The real estate agent has the possibility of geolocating all the properties present in the application using a Maps card as well, thanks to the position markers.
* The connected real estate agent, thanks to Firebase authentication procedures, can also create properties in his name and find them at the same time.


#### #Pictures of the project
***
* [Home screen capture](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/all-properties.jpeg) 
* [Add a property 1 ](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/add-property-1.jpeg)
* [Add a property 2 ](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/add-property-2.jpeg)
* [All property on Maps](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/Real-estate-on-maps.jpeg)
* [Head-of-property-sheet](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/Head-of-property-sheet.jpeg)
* [Maps-Static-Api](https://github.com/mmarliacy/P9-RealEstateManager/blob/main/P9%20PHOTOS/Maps-Static-Api.jpeg)

#### #Technologies used
***
A number of dependencies and libraries were used for this project, among them:
  * [Retrofit](https://square.github.io/retrofit/): Version 2.9.0
  * [Google Play Services]([https://github.com/JakeWharton/butterknife/](https://developers.google.com/maps/documentation/android-sdk/config)): Version 18.1.0
  * [Bumptech.glide](https://github.com/bumptech/glide): Version 4.11.0
  * [Greenrobot:eventbus](https://github.com/greenrobot/EventBus): Version 3.1.1
  * Additionally: [recyclerview-v7](https://developer.android.com/topic/libraries/support-library/packages#v7-recyclerview): Android version 28.0.0.

#### #How to install
***
1. Download the repository at the following URL: https://github.com/mmarliacy/OC_PROJETS/tree/main/Entrevoisins.
2. Extract the application installation folder from the "Entrevoisins" archive to a destination folder or to the desktop.
3. Open Android Studio.
4. In order to open the program on Android Studio, follow the following instructions from the Android Studio menu bar: **File -> Open -> Destination folder -> Neighbors**
5. Android Studio, with the intervention of Gradle will synchronize the project, in case of "deprecated" technologies or dependencies, update the project, clicking again on: **File -> Sync Project with Gradle Files**.
#### #Features developed
***
The goal was therefore for this project, to add these additional features:
  * When you click on a user, you should see:
   * a functional back button that would return to the home screen;
   * the avatar of the user;
   * the name of the user;
   * a favorites button which would add a neighbor to the favorites list;
  * A Favorites tab where users marked as favorites would display.

#### #Authors and Contributors
***
This project was made by @Deyine a speaker from OpenClassRooms, and I brought the additional features to it.
> Additionally, I would like to thank my mentor @ LionelMambingo for his support in implementing these.
> According to him, "Programming is learning, a succession of problems to solve that you get to know as you go"

#### #Android Studio version
***
This project was made using Android Studio 4.2.2, then updated version 2020.3.1 (


