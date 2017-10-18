# Android Kotlin Components
Starter boilerplate projects for Android, with Components Architecture implemented, written completely in Kotlin. Variations include Rx, Realm & Dagger usage.

[Explanation Article Here](https://medium.com/@ahmedrizwan)

## Basic Version 
Fetches Repositories from Github API 
- Rest API -> Retrofit 
- Database -> Room 
- Some cool Helper Classes based on [GithubBrowserExample](https://github.com/googlesamples/android-architecture-components)
- And a simple MainActivity that queries & shows the list of Repos

## BasicRx Version
- Basic version but utilizes Rx with Room & Retrofit

## Dagger Version
- Basic version but uses Dagger2 for Dependency Injection

## Realm Version
- Uses Realm instead of Room
- [Android Architecture and Realm](https://academy.realm.io/posts/android-architecture-components-and-realm/)

*TODOs are added to each project variation for an easy transition into making it your own.*

## License 
```
Copyright 2017 Ahmed Rizwan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
