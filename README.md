# BeeNameGenerator

[![License](https://img.shields.io/github/license/p0t4t0sandwich/BeeNameGeneratorPlugin?color=blue)](https://img.shields.io/github/downloads/p0t4t0sandwich/BeeNameGeneratorPlugin/LICENSE)
[![Github](https://img.shields.io/github/stars/p0t4t0sandwich/BeeNameGeneratorPlugin)](https://github.com/p0t4t0sandwich/BeeNameGeneratorPlugin)
[![Github Issues](https://img.shields.io/github/issues/p0t4t0sandwich/BeeNameGeneratorPlugin?label=Issues)](https://github.com/p0t4t0sandwich/BeeNameGeneratorPlugin/issues)
[![Discord](https://img.shields.io/discord/1067482396246683708?color=7289da&logo=discord&logoColor=white)](https://discord.neuralnexus.dev)
[![wakatime](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/0f240003-7202-4e04-810e-e83bb007db81.svg)](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/0f240003-7202-4e04-810e-e83bb007db81)

A Minecraft plugin that implements the [bee-name-generator](https://github.com/p0t4t0sandwich/bee-name-generator) API to
generate bee names automagically

Link to our support: [Discord](https://discord.neuralnexus.dev)

## Download

[![Github Releases](https://img.shields.io/github/downloads/p0t4t0sandwich/BeeNameGeneratorPlugin/total?label=Github&logo=github&color=181717)](https://github.com/p0t4t0sandwich/BeeNameGeneratorPlugin/releases)
[![Spigot](https://img.shields.io/spiget/downloads/112100?label=Spigot&logo=spigotmc&color=ED8106)](https://www.spigotmc.org/resources/beenamegenerator.112100/)
[![Hangar](https://img.shields.io/badge/Hangar-download-blue)](https://hangar.papermc.io/p0t4t0sandwich/BeeNameGenerator)
[![Modrinth](https://img.shields.io/modrinth/dt/beenamegenerator?label=Modrinth&logo=modrinth&color=00AF5C)](https://modrinth.com/mod/beenamegenerator)
[![CurseForge](https://img.shields.io/curseforge/dt/903965?label=CurseForge&logo=curseforge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/beenamegenerator)
[![Sponge](https://img.shields.io/ore/dt/beenamegenerator?label=Sponge&logo=https%3A%2F%2Fspongepowered.org%2Ffavicon.ico&color=F7CF0D)](https://ore.spongepowered.org/p0t4t0sandwich/BeeNameGenerator)

Jenkins: [![Jenkins Build](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fjenkins.neuralnexus.dev%2Fjob%2FBeeNameGenerator%2F)](https://jenkins.neuralnexus.dev/job/BeeNameGenerator/)

Maven: [![Maven Repo](https://img.shields.io/maven-metadata/v?label=Release&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Freleases%2Fdev%2Fneuralnexus%2FBeeNameGenerator%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/releases/dev/neuralnexus/BeeNameGenerator)

### Compatibility Cheatsheet

BeeNameGenerator supports: Bukkit, Fabric, Forge, and Sponge on versions 1.15 and up, all in one Jar.

## Dependencies

- [TaterLib](https://github.com/p0t4t0sandwich/TaterLib) - Required on all platforms

## Usage

- Commands either require a permissions manager or OP level 4 in order to use them.
- You can set the "payment" item in the config, which is the item that is consumed when using the `/bng name auto`
  command (defaults to a name tag).
- The naming radius can be set in the config, which is the radius around the player that the plugin will look for bees
  to name (defaults to 10 blocks).
- Some commands require an authenticated API key to use them, which is set in the config (you'd need to host your
  own [API endpoint](https://github.com/p0t4t0sandwich/bee-name-generator)).
- If you know of any nice bee-entity mods, let me know and I'll add support for them!

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

`<plugins/config>/BeeNameGenerator/beenamegenerator.conf`

```hocon
// Config version, DO NOT CHANGE THIS
version = 1

api = {
    // URL for the Bee Name Generator API
    baseUrl: "https://api.sperrer.ca/api/v1/bee-name-generator"
    // Auth Token for the Bee Name Generator API
    authToken: "YOUR_AUTH_TOKEN"
}

naming = {
    // Radius to search for bees in
    radius: 10,
    // Whether to allow duplicate names in the same area
    allowDuplicates: false
    // Radius to search for bees in if allow_duplicates is false
    duplicateRadius: 30,
    // Whether to require payment when naming bees
    requirePayment: true,
    // The item to use as payment for naming bees
    paymentItem: "minecraft:name_tag",
}
```

## Developer API

```gradle
repositories {
    maven {
        name = 'NeuralNexus'
        url = 'https://maven.neuralnexus.dev/releases' // or snapshots
    }
}

dependencies {
    compileOnly('dev.neuralnexus:beenamegenerator:<version>')
}
```

## Metrics

### Bukkit

![image](https://bstats.org/signatures/bukkit/BeeNameGenerator.svg)

### Sponge

![image](https://bstats.org/signatures/sponge/BeeNameGenerator.svg)
