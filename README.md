# SecurityCamera Plugin2

The **SecurityCamera** plugin for Minecraft allows players with the appropriate permissions to create, manage, and interact with security cameras within the game. This plugin can be used for surveillance, watching different areas of the world, or simply as an immersive feature for roleplay servers.

## Features

- **Create and manage cameras**: Admins or players with the appropriate permissions can create security cameras at specific locations within the game world.
- **Camera viewing**: Players can view cameras they have permission for, allowing them to see different areas of the world remotely.
- **Permissions system**: The plugin supports permissions, ensuring that only authorized players can create and view cameras.
- **Easy-to-use commands**: A simple command system allows players to list, view, or teleport to cameras.

## Installation

1. **Download** the plugin .jar file.
2. Place the .jar file in the **plugins** folder of your Minecraft server.
3. Restart or reload the server to load the plugin.

## Setup & Configuration

The plugin is designed to be very easy to configure. By default, cameras can be created by players with the permission `securitycamera.create`.

Just drag 'n drop the plugin into the plugins folder and you're done!
Feel free to customize the message in the config.yml however you want

## Commands

- `/camera` - **Watch a camera or see the list of available ones**. 
   - If no camera name is provided, this command will list all available cameras the player has permission to view.
   - If a camera name is provided, the player will be teleported to that camera's location in spectator mode.
  
  Example:
  ```
  /camera [camera_name]
  ```

## Permissions

### Default Permissions
- `securitycamera.create`: Allows the player to create a new security camera.
- `securitycamera.remove`: Allows the player to remove a camera.

For more information about how permission works, have a look at the docs.

### Example Permissions Setup (for LuckPerms or PermissionsEx):
```yaml
securitycamera:
  create: true
  view: true
  remove: true
```

## Usage

### Creating a Camera
To create a camera, an admin or authorized player can use the following command:

```
/createcamera [camera_name]
```

This will create a camera at the player's current location. The camera name must be unique. You can also set specific properties for the camera (such as its location or name) in the plugin's configuration.

### Viewing a Camera
To view a camera, use the command:

```
/camera [camera_name]
```

If the player has permission to view the camera, they will be teleported to the camera's location in **spectator mode**, which allows them to observe the area from the camera's point of view.

### Removing a Camera
To remove a camera, use the following command:

```
/removecamera [camera_name]
```

This command will delete the camera and its associated data from the system.

## Configuration

The `config.yml` file allows you to configure various messages so your server can be unique!

## `config.yml`

```yaml
prefix: "<yellow>[SecurityCamera]</yellow> "

messages:
  no-permission: "<red>You don't have enough permission!</red>"
  not-exist: "<red>Selected camera doesn't exist!</red>"
  camera-exists: "<red>A camera with this name already exist!</red>"
  how-to: "<green>You've entered Creating Mode! Please select a point in the roof where you would like the camera to be!</green>"
  already-creating: "<red>You are already in creating mode!</red>"
  exited: "<green>You have successfully exited creating mode!</green>"
  created: "<green>Successfully created a new camera!</green>"
  deleted: "<green>Camera successfully deleted!</green>"
  available-cams: "<green>List of available cameras:</green>"

```

## How It Works

1. **Creating a Camera**: When a player with permission runs the `/createcamera` command, a camera is created at their current position in the world.
2. **Viewing a Camera**: Players can view a specific camera by using the `/camera [camera_name]` command. The plugin will teleport them into **spectator mode** and show the camera's view.
3. **Removing a Camera**: If a player has permission to remove cameras, they can delete a camera using the `/removecamera [camera_name]` command.

This allows for seamless monitoring of specific areas, whether it's for security purposes, surveillance, or just for fun.

## Example Use Case

1. A server admin sets up several security cameras around their world (e.g., near entrances, vaults, or key areas).
2. Players with the proper permissions can use the `/camera` command to view those cameras and monitor areas of the world remotely.
3. Admins can remove or reassign cameras as necessary using the `/removecamera` or `/createcamera` commands.

## Troubleshooting

- **"Camera not found" error**: This typically means the camera you are trying to access either does not exist or you do not have permission to view it. Check the permissions and camera list.
- **"No permission" error**: Make sure the player has the correct permission for viewing or creating cameras (`securitycamera.view` or `securitycamera.create`).

## Compatibility

- **Minecraft versions**: This plugin is compatible with Minecraft versions 1.20.1 to Latest.
- **Dependencies**: The plugin does not require any external dependencies, the one needed are Shaded in.

## Contribution

I would love if you contributed to the SecurityCamera plugin! If you'd like to report bugs, suggest new features, or contribute code, please create an issue or a pull request on this repository.

## License

This plugin is open-source under the MIT license.
