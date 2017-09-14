# digdag-plugin-mysql

Digdag `mysql>` operator plugin to execute a query on MySQL sserver.

## configuration

[Release list](https://github.com/hiroyuki-sato/digdag-plugin-mysql/releases).

```yaml
_export:
  plugin:
    repositories:
    repositories:
      #- file://${repos}
      - file:///path/to/digdag-plugin-mysql/build/repo
      # - https://jitpack.io
    dependencies:
      - com.github.hiroyuki-sato:digdag-plugin-mysql:0.1.0

  mysql:
    host: localhost
    user: root
    database: digdag_test
    ssl: true

+step1:
  mysql>: test.sql
  download_file: test.txt
```

Register mysql password into secrets.

local mode 

```
digdag secrets --local --set mysql.password
```

server mode 

```
digdag secrets --project <project> --set mysql.password
```


## Development

### 1) build

```sh
./gradlew publish
```

Artifacts are build on local repos: `./build/repo`.

### 2) run an example

```sh
digdag selfupdate

rm -rf sample/.digdag/plugin 
digdag run -a --project sample plugin.dig -p repos=`pwd`/build/repo
```
