# BeeNameGenerator

A Minecraft plugin that implements the [bee-name-generator](https://github.com/p0t4t0sandwich/bee-name-generator) API to generate bee names automagically

Link to our support: [Discord](https://discord.neuralnexus.dev)

## Download

- [GitHub](https://github.com/p0t4t0sandwich/BeeNameGeneratorPlugin/releases)
- [Maven Repo](https://maven.neuralnexus.dev/#/releases/dev/neuralnexus/BeeNameGenerator)
- [Spigot](https://www.spigotmc.org/resources/beenamegenerator.112100/)
- [Hangar](https://hangar.papermc.io/p0t4t0sandwich/BeeNameGenerator)
- [Modrinth](https://modrinth.com/plugin/beenamegenerator)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/beenamegenerator)
- [Sponge](https://ore.spongepowered.org/p0t4t0sandwich/BeeNameGenerator)

### Compatibility Cheatsheet

BeeNameGenerator supports: Bukkit, Fabric, Forge, and Sponge (some versions)

| Server type        | Versions    | Jar Name                              |
|--------------------|-------------|---------------------------------------|
| All 1.20           | 1.20-1.20.1 | `BeeNameGenerator-<version>-1.20.jar` |
| All 1.19           | 1.19-1.19.4 | `BeeNameGenerator-<version>-1.19.jar` |
| All 1.18           | 1.18-1.18.2 | `BeeNameGenerator-<version>-1.18.jar` |
| All 1.17           | 1.17-1.17.1 | `BeeNameGenerator-<version>-1.17.jar` |
| All 1.16 (Sponge8) | 1.16-1.16.5 | `BeeNameGenerator-<version>-1.16.jar` |
| All 1.15           | 1.15-1.15.2 | `BeeNameGenerator-<version>-1.15.jar` |

## Dependencies

- [TaterLib](https://github.com/p0t4t0sandwich/TaterLib) - Required on all platforms
- [FabricAPI](https://modrinth.com/mod/fabric-api) - Required on Fabric

### Optional Dependencies

- [LuckPerms](https://luckperms.net/) - For permissions/prefix/suffix support

## Usage

- Commands either require a permissions manager or OP level 4 in order to use them.
- You can set the "payment" item in the config, which is the item that is consumed when using the `/bng name auto` command (defaults to a name tag).
- The naming radius can be set in the config, which is the radius around the player that the plugin will look for bees to name (defaults to 10 blocks).
- Some commands require an authenticated API key to use them, which is set in the config (you'd need to host your own [API endpoint](https://github.com/p0t4t0sandwich/bee-name-generator)).

## Commands and Permissions

| Command                                  | Permission                   | Description                                               |
|------------------------------------------|------------------------------|-----------------------------------------------------------|
| `/bng help`                              | `bng.command.help`           | Show help for commands                                    |
| `/bng reload`                            | `bng.command.reload`         | Reload the plugin                                         |
| `/bng name <auto/tag>`                   | `bng.command.name`           | Name a bee or get a named name tag                        |
| `/bng name auto`                         | `bng.command.name.auto`      | Automatically name a nearby bee                           |
| `/bng name tag`                          | `bng.command.name.tag`       | Get a name tag with a random bee name                     |
| `/bng get`                               | `bng.command.get`            | Gets a random bee name                                    |
| `/bng add`                               | `bng.command.add`            | Adds a bee name to the database (Authenticated API Route) |
| `/bng suggest <name/list/accept/reject>` | `bng.command.suggest`        | Suggest a name to the database                            |
| `/bng suggest <name>`                    | `bng.command.suggest`        | Suggest a name to the database                            |
| `/bng suggest list`                      | `bng.command.suggest.list`   | List all pending suggestions                              |
| `/bng suggest accept <name>`             | `bng.command.suggest.accept` | Accept a pending suggestion (Authenticated API Route)     |
| `/bng suggest reject <name>`             | `bng.command.suggest.reject` | Reject a pending suggestion (Authenticated API Route)     |

## Configuration

`<plugins/config>/BeeNameGenerator/beenamegenerator.config.yml`

```yaml
---
version: 1

api:
  # URL for the Bee Name Generator API
  base_url: "https://api.sperrer.ca/api/v1/bee-name-generator"

  # Auth Token for the Bee Name Generator API
  auth_token: "YOUR_AUTH_TOKEN"

naming:
  radius: 10
  payment_item: "minecraft:name_tag"
```

## TODO

## Release Notes
- Initial Release
