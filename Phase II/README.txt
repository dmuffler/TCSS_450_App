SmartConvert README
Author: Irene Fransiga, Donald Muffler, Joshua Lau
Project Phase: 2



Project Requirements:
1. Fix any bugs from the previous phase that the instructor or peer reviewers found.
	Bugs from previous phase that were fixed:
	A. Changed the radio buttons in tutorial page 3 to just text because
	the radio button isn’t doing anything another than just showing information.

	B. Tutorial page 3 have skip option but there’s nothing to skip anymore. 
	We changed the text to exit.
	
	C. API key were limited to 5 calls per month. API key were blocked so no one
	was able to make calls. We bought an API key and there has not been any problem
	with the API key and calls limit.

	Bugs from previous phase that were not fixed: none

2. You must implement 90% of the use cases that you provided in your proposal.
	We listed 7 use cases and we managed to accomplish 6 out of 7, here are our use cases:
	A. Register
	B. Continue as a guest, we called it “skip”
	C. Login
	D. Tutorial Page
	E. Set Country to Default
	F. Convert Currency

	Use Cases that are not implemented:
	A. Manually set the default country and currency. 
	This use cases falls under not implemented because user is not able to manually set the default country.
	However, user is able to manually change the currency in the spinner whenever they like.

3. Your app must save data to the device’s storage using SQLite or 
   one other storage mechanism (SharedPreferences or File Storage).
	Our app does both SQLite and SharedPreferences. We have two database tables,
	one is called UserInfo where we keep UserID, User’s First Name and User’s Last Name. 
	The second one is called LoginData where we keep UserID, Email, encrypted password,
	the confirmation code, and confirmation boolean. We chose to keep these information
	in the database because we can use the UserID to make sure that we got the information
	correct and parallel to each other. Another reason is that database is an external
	storage where every user can access to their login information everywhere in any devices.
	
	We use SharedPreferences to keep data such as login username, location name, location 
	code, and <————>. We believe that these are better kept in SharedPreferences because no 
	one else needs this information other than the device and the user. Thus we don’t have to 
	keep it externally.

4. Your app must use web services to make the app functional.
	Yes, our app is using web services. Here are the web services that we use:
	A. Free Currency API by Amdoren 	https://www.amdoren.com/currency-api/
	This web services is called in the convert page where user convert values. 
	We use this web service because this API helps us convert currency with real time rate update.

	B. Country API by Fabian7593		https://fabian7593.github.io/CountryAPI/
	This web service is called in the convert page. We use this API to find the country’s currency code
	using country’s code. This API is just what we needed because we are using user’s location to get the
	currency in that country and his API was super easy to use and free.

	C. Google Location API by Google
	This web service is called in the main activity page, we called it there so that it is always active
	and updating. Using this API, we got coordinate of latitude and longitude then we get the country’s name
	and country’s 2 letter acronym to be send to Country API (above api).

5. Your app must allow Sign-In and Register.
	Yes, our app allow Sign-in and Register

6. Your app uses graphics to display images.
	Yes, we have an icon and a logo for our app. 

7. Meeting notes link: https://docs.google.com/document/d/19KkbCvxdy4WRDITyhckGlgHHKMgYFVZbpnJL9J8Xn1w/edit?usp=sharing
