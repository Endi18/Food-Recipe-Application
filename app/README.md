# Tasty - A Food Recipes Application

## Team Members

* Sara Hoxha
* Endi Triço
* Sexhi Picaku

## Project Description

For the Mobile Application Development course, we decided to develop a food recipes application. With our app, named "Tasty", users can either opt to log in or continue in guest mode so as to access a vast collection of delicious recipes from different cuisines all around the world.

Inside our app, we have incorporated a search function that allows users to easily browse through hundreds of recipes simply by entering a keyword. Moreover, the "Tasty" app also allows users to like their favorite recipes, making it easy to save and revisit them later, in their own dedicated "Favorites" page.

Overall, thanks to its user-friendly interface and extensive recipe collection, our food recipes app is a must-have for anyone who loves to cook and experiment with new flavors, all through the convenience of their own mobile phone.

## Tasks implemented

### Classes

#### APIRequestManager
##### _Author: Sara Hoxha & Endi Triço_

To make API requests to retrieve recipe information from the Spoonacular API, we have implemented the `APIRequestManager` class. The class contains two constructors, one for searching recipes by name and another for searching recipes information by recipes ID. Moreover, the class utilizes Retrofit library to handle network requests and responses.

Inside this class, we have defined the `searchRecipes` interface, which defines a method that makes a GET request to the Spoonacular API, namely the Search Recipes function, to search for recipes based on three parameters:
* query ->  specifies the keyword that will be used to search for recipes
* number -> specifies the maximum number of results returned
* apiKey -> specifies the unique API Key used to make the request

Another interface defined in the class is the `recipeInformation` interface, which defines a method that makes a GET request to the Spoonacular API, namely the Get Recipe Information function, to retrieve recipe information for a specific recipe based on two parameters:
* id -> recipe ID
* apiKey -> specifies the unique API Key used to make the request

Moreover, the class contains two methods to get search results and to get the recipe information. These methods are: `getRecipesSearchResults` and `getRecipeInformationSearchResults`. These methods take a listener object to receive callbacks upon successful or failed completion of the API request. For both methods, if the API response is successful, the `didFetch` method is called on the listener object with the response body and message. If the API response is unsuccessful, the `didError` method is called on the listener object with an error message.

The `getRecipesSearchResults` takes as listener the APISearchResponseListener, whereas the `getRecipeInformationSearchResults` takes the APIRecipeInformationResponseListener listener.

#### APIRecipeInformationResponseListener 
##### _Author: Endi Triço_

The `APIRecipeInformationResponseListener` is an interface that defines two methods that must be implemented by any class that wants to listen for and handle the response of an API request made to retrieve information for a specific recipe from the Spoonacular API.

The didFetch method is called when the API request is successful and the response contains the information for the requested recipe. This method takes in the APIRecipeInformationResponse object as a parameter, which contains the recipe information, and a message string that describes the status of the response.

The didError method is called when there is an error in the API request and the response is not successful. This method takes in a message string that describes the error that occurred.

#### APISearchResponseListener
##### _Author: Sara Hoxha_

The `APISearchResponseListener` is an interface that defines two methods that must be implemented by any class that wants to listen for and handle the response of an API request made to retrieve recipes from the Spoonacular API.

The didFetch method is called when the API request is successful and the response contains the information for the requested recipe. This method takes in the APISearchResponse object as a parameter, which contains the recipe information, and a message string that describes the status of the response.

The didError method is called when there is an error in the API request and the response is not successful. This method takes in a message string that describes the error that occurred.


#### RecipeSearchResultAdapter
##### _Author: Sara Hoxha_

The `RecipeSearchResultAdapter` class is used to display a list of recipes obtained from the Spoonacular API call.

The `RecipeSearchResultAdapter` class extends the `RecyclerView.Adapter` class and has a constructor that takes two parameters, a `Context` object, and a list of `Recipe` objects.

The `onCreateViewHolder()` method creates a new instance of the `RecipeSearchResultViewHolder` class, which contains a `CardView` that displays the recipe information. The `getItemCount()` method returns the number of recipes in the list.The `onBindViewHolder()` method binds the data from the `Recipe` object to the `RecipeSearchResultViewHolder`.

#### RecipeSearchResultViewHolder
##### _Author: Sara Hoxha_

The `RecipeSearchResultViewHolder` class is an inner class that extends `RecyclerView.ViewHolder`. It has a constructor that takes a `View` object and initializes the card view, the dish name, and the dish image that will be displayed in the layout.


### Activities

Delving into more detail, we have created multi-activities that allow users to perform various actions inside the "Tasty" application. These are those activities:

#### Initial Activity
##### _Author: Sara Hoxha_

When the user first launches the application, he is shown an acitivty that allows the user to opt for authentication or to continue using our application in Guest Mode. This choice is facilitated through the use of Buttons. If the user clicks on the "Log in" button, he is redirected to the Login Activity. Alternatively, if the user clicks on the "Guest Mode" button, he is directly redirected to the Search Recipes Activity.This redirection is done through the use of explicit intents.


#### Login Activity
##### _Author: Sara Hoxha_

After being redirected to the Login Activity, the user will be prompted to enter their email and password. After finishing entering their credentials, the user can try to authenticate and enter the application by clicking on the button "Log In". Firstly, after the user has clicked this button, we check if the user has provided an email and password, and if the email is in valid format. If these conditions are not fulfilled, we display an error message to the appropriate TextView and require the user to amend this data. If this data is entered and in valid format, we check these credentials checked against the data stored in our SQLite database. The process of authentication works by querying the database to see if the entered email and password exists in our database. If a match is found, the user is authenticated and they are redirected to the "Search Activity", through the use of an explicit intent that also sends the authenticated user ID as part of the message. On the other hand, if there is not a match found, the application will display an error toast message indicating that the login failed due to invalid data.

Furthermore, we also have the option for the user to register if they haven't already. This is done by clicking on the "Sign Up Now" label, which redirects them to the Sign Up Activity.


#### Recipe Search Result Activity
##### _Author: Sara Hoxha_

The `RecipeSearchResultActivity` is used to display the search results of recipes, based on the keyword provided by the user.

Inside this activity, we have initialized a `ProgressDialog` to show a loading spinner while the search results are being fetched.

We also initialize an instance of the `APIRequestManager` class with a search keyword, that we get from the "Search Recipe" Activity and we call its `getRecipesSearchResults()` method to fetch the search results from the API.

After the results are fetched, the spinner is dismissed and the results are displayed in a `RecyclerView` using the `RecipeSearchResultAdapter` class to provide the adapter for the recycler view.

The `goBackToSearchPage()` method is called when the user clicks the "Back" button on the activity. This uses an explicit intent to navigate the user back to the "Search Recipe" activity.


## External Resources
To provide a way for the "Tasty" application to access and retrieve data about various food recipes, ingredients, and nutritional information, we have used the Spoonacular Food API. Through using two API functions, namely Search Recipes and Get Recipe Information, we were able to offer a vast array of recipes that are updated regularly and provide our users with accurate information about ingredients, measurements, and nutritional values for their chosen recipes.

**Spoonacular Food API** - https://spoonacular.com/food-api

To simplify the process of making network requests and handling APIs in our Android application, we have utilized the popular open-source library Retrofit.
**Retrofit** - https://github.com/square/retrofit

To simplify the process of loading and displaying images in our Android application, we have utilized the popular open-source library Picasso.
**Picasso** - https://github.com/square/picasso
