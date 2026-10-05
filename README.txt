AutoVerify (Fabric 1.21.1, client-side)

Build with no local setup (GitHub):
1. Create a new GitHub repo and upload everything in this folder (keep the .github folder).
2. Open the Actions tab -> "build" -> wait for it to finish.
3. Download the "autoverify-jar" artifact, unzip it, and use autoverify-1.0.0.jar
   (NOT the -sources jar).
4. Put the jar in your mods folder with Fabric API 0.102.0+1.21.1.

Build locally: install JDK 21 and Gradle 8.10+, then run: gradle build
The jar appears in build/libs/.
