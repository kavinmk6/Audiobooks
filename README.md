# Audiobooks
Sample test app to display list of podcasts using audiobooks test API

The app was coded fully with kotlin following MVVM pattern. The Main libraraies which are used Retrofit for handling remote storage, Room for handling local storage, Glide for image processing. It also includes some the basic kotlin libraries.
The app needs to fetch podcasts details Via API and display it in list. When the user selects any podcast from the list, it will navigate to the second screen and display its details. In addition to that in secondscreen, we also have to add a favourite button to make the podcast favourite. After making making favourite, when we navigate back to the Mainscreen, the list should update with the Favourite keyword for the respective podcast.
So Basically the functionality of the app is, When the user opens the MainActivity (First Screen or launcher Screen), it will fetch the data by calling the API Provided in the documentation with the help of retrofit. The Api contains list of podcasts and its details. 
By default, we have the requirement to add a favourite functionality. But unfortunately, there is no field in the API to capture this favourite state. 
So,here i have taken the advantage of the room. So when the user makes the API call, all the fetched data will be processed in the background thread using coroutines and return as new object called PodcastFavourite.
This PodcastFavourite object has 5 attributes such as podcast name, podcast author, podcast image, podcast id which is autogenerate and primary key, and Podcast IsFavourite.
The last attribute PodcastIsFavourite is added explicitly to hold the favourite state of the podcast.
Hence, overall we are not going to fetch the data continously from the API each and every time. As our requirement involves handling favourite state explicitly, we are going to fetch data from API when the app launches first time.
After that, each and every time when the app opens or resumes, we are going to check the local database whether its empty or not. So, if the local database is empty, we will fetch new data from the API and process it, then store it locally by wrapping it into PodcastFavourite class.
Otherwise, we will fetch the data already stored in the local storage and display it in the app. So that we can maintain the state of the favourited podcasts.

There might be a question Arise???? what if all the datas in the Remote Storage changes. In that case, we need to modify the app function. The function is,
When ever the app opens, we need to fetch data from the remote storage and check those particular attributes with their respective attributes of the PodcastFavourite class. 
So if there is a change, then  we need to update our local storage with the new values fecthed from the remote storage.

Does it solves the complete requirement?????? Even though, we implement the above functionlity, we can't gauarantee to maintain the state of favourited podcast.

The app functions with respect to classes,
(First Screen) MainActivity -> will communicate with its Viewmodel class called MainSearchViewmodel -> MainSearchViewmodel will communicate with its repository class called MainActivityRepository -> MainActivityRepository will communicate to Retrofit Instance class and ApiService interface (for Remote storage)  or PodcastDatabase and PodcastDao (For LocalStorage).
PodcastAdapter class -> Handles displaying all the Podcasts in a list. As we are processing and storing all the Api data in the local database by wrapping it with PodcastFavourite class. This adapter will always receive list of data's wrapped with the PodcastFavourite class. Simply the model class for the adapter is always PodcastFavourite class.
(Second Screen) PodcastDetailActivity -> when the user interacts with the favourite button in this activity, it will communicate with its ViewModel class called PodcastDetailViewModel.
This PodcastDetailViewModel -> will communicate with its repository class called MainActivityRepository (YES, WE ARE USING SAME REPOSITORY FOR BOTH OF THE ACTIVITY AS WE DON'T HAVE ANY STRONG DEPENDECY AND TO MKAE IT SIMPLE) -> This MainActivityRepository will comunicate with PodcastDao interface -> this interface will invoke a method called update, which will update the Favourite attribute associated with the id value as either true or false.
When the user presses back button from PodcastDetailActivity, the app redirect back to MainActivity and refresh's the recyclerview and now you can see a new textfield called FAVOURITED in the particular podcast.
