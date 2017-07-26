Math Quiz Bot for Telegram
==========================

Simple math quiz bot for [Telegram](https://telegram.org/).

Uses [Bot API 2.0](https://core.telegram.org/bots/api/).

Building
--------

You need JDK >= 1.8

    $ ./mvnw clean package

Running
-------

Set bot token in `application.properties` file.
See `application.sample.properties` for example configuration.

Run:

    $ java -Xmx32m -jar math-quiz-bot.jar

License
-------

BSD, see `LICENSE` for full text.
