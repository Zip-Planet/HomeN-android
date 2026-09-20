package com.devndev.homen.core.domain.model.board

enum class BoardType(val type: String) {
    BOT("bot"),
    REQUEST_HELP("help"),
    REQUEST_EXCHANGE("swap")
}

enum class HelpBoardType(val type: String) {
    PENDING("pending"),
    ACCEPTED("accepted"),
    EXPIRED("expired")
}

enum class ExchangeBoardType(val type: String) {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    EXPIRED("expired")
}
