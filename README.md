# City Weather Dashboard App 🌤️

This is an Android application that allows users to search for cities and view real-time weather information. The app also supports user login and lets users save their favorite cities using Firebase.

---

## Features

### 🔍 City Search
- Search any city worldwide
- Displays matching results in a list
- Shows city name, region, and country
- Tap a city to view weather details

### 🌦️ Weather Dashboard
- Current temperature (°C / °F)
- Weather condition with icon
- Feels like temperature
- Humidity level
- Wind speed and direction
- Card-based layout

### 🔐 Authentication
- Email and password login/register
- Firebase Authentication
- Auto login if user already signed in
- Sign out option

### ⭐ Saved Cities
- Save favorite cities per user
- View saved cities in real time
- Delete saved cities
- Firebase Firestore integration

---

## Tech Stack

- Java / Kotlin
- MVVM Architecture
- Fragments (FragmentManager)
- LiveData
- ViewBinding
- RecyclerView
- OkHttp
- Firebase Authentication
- Cloud Firestore
- WeatherAPI

---

## Architecture

The app follows MVVM structure:

- **View (Fragments)** → UI layer
- **ViewModel** → Handles UI state using LiveData
- **Repository** → Manages Firebase + API calls
- **Model** → Data classes

---

## App Flow

1. User logs in or registers
2. Searches for a city
3. Views weather dashboard
4. Saves favorite cities
5. Views saved cities list
6. Deletes cities if needed
7. Signs out

---

## APIs Used

- WeatherAPI (city search + weather data)
- Firebase Authentication
- Firebase Firestore

---

## Setup Instructions

1. Clone the repository
2. Open in Android Studio
3. Add `google-services.json` (Firebase setup)
4. Add WeatherAPI key
5. Sync Gradle
6. Run the app

---

## Developer

Basma Ali

---


