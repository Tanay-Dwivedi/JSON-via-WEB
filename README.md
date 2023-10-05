# JSON-via-WEB

**[Explanation file](https://github.com/Tanay-Dwivedi/JSON-via-WEB/blob/master/app/Explanation%20File)**

***Overview:-***

This Android app serves the purpose of fetching and displaying employee data from a remote source. When the app is launched, it presents a user interface featuring a button. Upon clicking this button, the app initiates a network request to retrieve [JSON data](https://api.npoint.io/ad7418864a4c75a029ac) from a specific location. While the data is being fetched, a progress dialog is displayed, informing the user that the process is ongoing.

Once the JSON data is successfully retrieved, the app parses it to extract information about employees, including their names, ages, and cities. This extracted data is then presented in a ListView within the app's user interface.

Upon completing the data fetching and parsing tasks, the app dismisses the progress dialog, ensuring a seamless and user-friendly experience. In essence, this app simplifies the process of obtaining and displaying employee information from a remote source, making it accessible to users with just a click of a button.
