# CircleBinder for Android [![Circle CI](https://circleci.com/gh/CircleBinder/Android-Application.svg?style=svg)](https://circleci.com/gh/CircleBinder/Android-Application)

## Libraries Used

- Application
    - Butter Knife
    - RxAndroid
- Testing
    - Espresso
    - Robolectric

## Build

In your local machine:

``` sh
# make and apk and install it to the connected device
./gradlew installDebug
```

To test it with Docker (what circleci.yml does):

``` sh
docker build -t circlebinder/android-application .
docker run -it circlebinder/android-application
```

## License

This application is free software; you can redistribute it and/or modify it under the ters of the Apache License 2.0.

- http://www.apache.org/licenses/LICENSE-2.0

