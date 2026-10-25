# Maestro flows

Flows are organized by feature under `features/`; the end-to-end scenario is in `journeys/`.
The feature flows are reusable steps and assume the app is already on the screen they exercise.
The journey runs them in order, preserving one app session from login through logout. Its login
step deliberately submits an invalid username, checks the validation message, corrects it, and
then continues after successful login. The journey opens Details, returns to Dashboard, and logs
out from the Dashboard screen.

Run the complete flow:

```powershell
maestro test .maestro/journeys/login-dashboard-details-logout.yaml
```

Install the debug app first if needed:

```powershell
.\gradlew.bat :app:installDebug
```

The journey clears app state once at startup. Nested feature flows do not relaunch or clear
the app, so navigation state is carried between Login, Dashboard, and Details.
