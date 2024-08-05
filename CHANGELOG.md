# Changelog

## [2.0.0](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.2.3...v2.0.0) (2024-08-05)


### ⚠ BREAKING CHANGES

* require `packageName` to be defined ([#33](https://github.com/Optravis-LLC/jooq-gradle/issues/33))

### Features

* require `packageName` to be defined ([#33](https://github.com/Optravis-LLC/jooq-gradle/issues/33)) ([9776e55](https://github.com/Optravis-LLC/jooq-gradle/commit/9776e55db273afbfd3e8b0ae2ee43ac0828a7251))


### Bug Fixes

* **deps:** update flyway to v10.16.0 ([#35](https://github.com/Optravis-LLC/jooq-gradle/issues/35)) ([c283bc0](https://github.com/Optravis-LLC/jooq-gradle/commit/c283bc0177d11ec00964fa91b6a6ed7f3c3e48b5))
* **deps:** update flyway to v10.17.0 ([#37](https://github.com/Optravis-LLC/jooq-gradle/issues/37)) ([dac62e2](https://github.com/Optravis-LLC/jooq-gradle/commit/dac62e2536aed465280164baa7bbf839e1fb9698))
* **deps:** update testcontainers-java monorepo to v1.20.0 ([#34](https://github.com/Optravis-LLC/jooq-gradle/issues/34)) ([37f3f12](https://github.com/Optravis-LLC/jooq-gradle/commit/37f3f12ac67fc35c51e4734c027b033faf590094))
* **deps:** update testcontainers-java monorepo to v1.20.1 ([#38](https://github.com/Optravis-LLC/jooq-gradle/issues/38)) ([35bb08e](https://github.com/Optravis-LLC/jooq-gradle/commit/35bb08ec5a609028496db6029b4ef6db4f889a1e))

## [1.2.3](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.2.2...v1.2.3) (2024-07-11)


### Bug Fixes

* Default generated packge to `org.jooq.generated` if the project `group` is not defined ([#27](https://github.com/Optravis-LLC/jooq-gradle/issues/27)) ([f769c0e](https://github.com/Optravis-LLC/jooq-gradle/commit/f769c0ef2e2aba3801c251962b49e7887420b4c6))

## [1.2.2](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.2.1...v1.2.2) (2024-07-02)


### Bug Fixes

* **deps:** update dependency org.flywaydb:flyway-core to v10.15.2 ([#23](https://github.com/Optravis-LLC/jooq-gradle/issues/23)) ([cebf12d](https://github.com/Optravis-LLC/jooq-gradle/commit/cebf12d6af44d45081ef54ed348adb3fa1c0dee8))

## [1.2.1](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.2.0...v1.2.1) (2024-06-20)


### Bug Fixes

* more robust equality check for `GeneratorType` ([#21](https://github.com/Optravis-LLC/jooq-gradle/issues/21)) ([b0a8452](https://github.com/Optravis-LLC/jooq-gradle/commit/b0a84523bcf6ddf8aa9a551c03c8c785d25e88d3))

## [1.2.0](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.2.0...v1.2.0) (2024-06-20)


### Features

* Extend Generator Configuration to capture Type ([#16](https://github.com/Optravis-LLC/jooq-gradle/issues/16)) ([5df24a0](https://github.com/Optravis-LLC/jooq-gradle/commit/5df24a0cc4b5f1a258d2395dd2a7e4b5cd47cb01))



## [1.1.0](https://github.com/Optravis-LLC/jooq-gradle/compare/v1.0.0...v1.1.0) (2024-06-19)


### Features

* `ContainerConfig.postgres()` helper function ([#14](https://github.com/Optravis-LLC/jooq-gradle/issues/14)) ([964dffb](https://github.com/Optravis-LLC/jooq-gradle/commit/964dffb8a53ce84927eab2ea98a46950d4a3b07f))
* Add configuration for version fields ([#8](https://github.com/Optravis-LLC/jooq-gradle/issues/8)) ([bf4b580](https://github.com/Optravis-LLC/jooq-gradle/commit/bf4b5801fa3f02ac620e8fb8ac1602d2857daf3d))
* Configure javaTimeTypes ([#10](https://github.com/Optravis-LLC/jooq-gradle/issues/10)) ([4a1e072](https://github.com/Optravis-LLC/jooq-gradle/commit/4a1e0722e3451052da669064e4c92876e3c81263))
* Option to not apply pojos as kotlin data classes ([#9](https://github.com/Optravis-LLC/jooq-gradle/issues/9)) ([64e1438](https://github.com/Optravis-LLC/jooq-gradle/commit/64e1438f84ac92fc7bf4217f94d826450500e5b7))
* rename extension to `jooqGenerator` ([290ef77](https://github.com/Optravis-LLC/jooq-gradle/commit/290ef77016724aef8e53d04932f99ef461bde33d))

## 1.0.0 (2024-06-17)


### Features

* jooq generator plugin ([aab4e4c](https://github.com/Optravis-LLC/jooq-gradle/commit/aab4e4c0affdaa36bf36959b7169a8df0c575ad0))
