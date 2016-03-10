package lila

import kamon.Kamon.metrics

object mon {

  object http {
    object request {
      val all = inc("http.request.all")
    }
    object response {
      val code400 = inc("http.response.4.00")
      val code404 = inc("http.response.4.04")
      val code500 = inc("http.response.5.00")
      val home = rec("http.response.home")
      object user {
        object show {
          val website = rec("http.response.user.show.website")
          val mobile = rec("http.response.user.show.mobile")
        }
      }
      object tournament {
        object show {
          val website = rec("http.response.tournament.show.website")
          val mobile = rec("http.response.tournament.show.mobile")
        }
      }
      object player {
        val website = rec("http.response.player.website")
        val mobile = rec("http.response.player.mobile")
      }
      object watcher {
        val website = rec("http.response.watcher.website")
        val mobile = rec("http.response.watcher.mobile")
      }
    }
  }
  object lobby {
    object hook {
      val create = inc("lobby.hook.create")
      val join = inc("lobby.hook.join")
      val size = rec("lobby.hook.size")
    }
    object seek {
      val create = inc("lobby.seek.create")
      val join = inc("lobby.seek.join")
    }
    object socket {
      val getUids = rec("lobby.socket.get_uids")
      val member = rec("lobby.socket.member")
    }
  }
  object round {
    object api {
      val player = rec("round.api.player")
      val watcher = rec("round.api.watcher")
    }
    object actor {
      val member = rec("round.actor.online")
    }
    object forecast {
      val create = inc("round.forecast.create")
    }
    object move {
      val time = rec("round.move")
      val count = inc("round.move")
    }
  }
  object explorer {
    object index {
      val success = inc("explorer.index.success")
      val failure = inc("explorer.index.failure")
      val time = rec("explorer.index.time")
    }
  }
  object timeline {
    val notification = incX("timeline.notification")
  }
  object insight {
    val request = rec("insight.request")
  }
  object search {
    def client(op: String) = rec(s"search.client.$op")
  }
  object jvm {
    val thread = rec("jvm.thread")
    val daemon = rec("jvm.thread")
    val uptime = rec("jvm.thread")
  }
  object user {
    val online = rec("user.online")
    object register {
      val website = inc("user.register.website")
      val mobile = inc("user.register.mobile")
    }
  }
  object socket {
    val member = rec("socket.count")
    val open = inc("socket.open")
    val close = inc("socket.close")
  }
  object mod {
    object report {
      val unprocessed = rec("mod.report.unprocessed")
    }
    object log {
      val create = inc("mod.log.create")
    }
  }
  object cheat {
    val cssBot = inc("cheat.css_bot")
  }
  object email {
    val resetPassword = inc("email.reset_password")
    val confirmation = inc("email.confirmation")
    val disposableDomain = rec("email.disposable_domain")
  }
  object security {
    object tor {
      val node = rec("security.tor.node")
    }
    object firewall {
      val block = inc("security.firewall.block")
      val ip = rec("security.firewall.ip")
    }
  }
  object tv {
    val streamer = rec("tv.streamer")
  }
  object relation {
    val follow = inc("relation.follow")
    val unfollow = inc("relation.unfollow")
    val block = inc("relation.block")
    val unblock = inc("relation.unblock")
  }
  object tournament {
    object pairing {
      val create = incX("tournament.pairing.create")
      val createTime = rec("tournament.pairing.create_time")
    }
    val created = rec("tournament.created")
    val started = rec("tournament.started")
    val player = rec("tournament.player")
  }
  object donation {
    val goal = rec("donation.goal")
    val current = rec("donation.current")
    val percent = rec("donation.percent")
  }
  object forum {
    object post {
      val create = inc("forum.post.create")
    }
    object topic {
      val view = inc("forum.topic.view")
    }
  }
  object puzzle {
    object selector {
      val count = inc("puzzle.selector")
      val time = rec("puzzle.selector")
    }
  }
  object opening {
    object selector {
      val count = inc("opening.selector")
      val time = rec("opening.selector")
    }
  }
  object analysis {
    def success(ip: String) = inc(s"analysis.$ip.success")
    def fail(ip: String) = inc(s"analysis.$ip.fail")
    def time(ip: String) = rec(s"analysis.$ip")
  }
  object game {
    def finish(status: String) = inc(s"game.finish.$status")
    object create {
      def variant(v: String) = inc(s"game.create.variant.$v")
      def speed(v: String) = inc(s"game.create.speed.$v")
      def source(v: String) = inc(s"game.create.source.$v")
      def mode(v: String) = inc(s"game.create.mode.$v")
    }
  }
  object chat {
    val message = inc("chat.message")
  }
  object push {
    def register(platform: String) = inc(s"push.register.$platform")
    val move = inc("push.move")
    val finish = inc("push.finish")
    object challenge {
      val create = inc("push.challenge.create")
      val accept = inc("push.challenge.accept")
    }
  }
  object ai {
    object client {
      val play = rec("ai.client.play")
    }
  }

  type Rec = Int => Unit
  type Inc = () => Unit
  type IncX = Int => Unit
  type RecPath = lila.mon.type => Rec

  def recPath(f: lila.mon.type => Rec): Rec = f(lila.mon)

  private def inc(name: String): Inc = metrics.counter(name).increment _
  private def incX(name: String): IncX = metrics.counter(name).increment(_)
  private def rec(name: String): Rec = metrics.histogram(name).record(_)
}