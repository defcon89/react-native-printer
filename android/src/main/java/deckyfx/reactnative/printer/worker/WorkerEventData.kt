package deckyfx.reactnative.printer.worker

import androidx.work.WorkInfo
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import deckyfx.reactnative.printer.escposprinter.PrinterSelectorArgument
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
class WorkerEventData(
  var selector: PrinterSelectorArgument? = null,
  var file: String? = null,
  var jobId: String? = null,
  var jobName: String? = null,
  var jobTag: String? = null,
  var state: String? = null,
  var id: String? = null,
  var tags: ReadableArray? = null,
  var generation: Int = 0,
  var runAttemptCount: Int = 0,
  var error: String? = null,
) {
  val writableMap: WritableMap
    get() {
      return Arguments.createMap().apply {
        putMap("selector", selector?.readableMap)
        putString("file", file)
        putString("jobId", jobId)
        putString("jobName", jobName)
        putString("jobTag", jobTag)
        putString("state", state)
        putString("id", id)
        putArray("tags", tags)
        putInt("generation", generation)
        putInt("runAttemptCount", runAttemptCount)
        putString("error", error)
      }
    }

  val json: String
    get() {
      val serializer = Json.serializersModule.serializer<WorkerEventData>()
      return Json.encodeToString(serializer, this)
    }

  companion object {
    fun fromJson(json: String): WorkerEventData {
      val serializer = Json.serializersModule.serializer<WorkerEventData>()
      return Json.decodeFromString(serializer, json)
    }

    fun fromWorkInfo(workInfo: WorkInfo): WorkerEventData {
      val data = WorkerEventData()
      val result = Arguments.createMap().apply {
        if (workInfo.progress.keyValueMap.isNotEmpty()) {
          val selector = PrinterSelectorArgument()
          workInfo.progress.getString("connection")?.let {
            selector.connection = it
          }
          workInfo.progress.getString("address")?.let {
            selector.address = it
          }
          workInfo.progress.getInt("port", 0).takeIf { it > 0 }?.let {
            selector.port = it
          }
          workInfo.progress.getInt("baudrate", 0).takeIf { it > 0 }?.let {
            selector.baudrate = it
          }
          workInfo.progress.getInt("dpi", 0).takeIf { it > 0 }?.let {
            selector.dpi = it
          }
          workInfo.progress.getFloat("width", 0f).takeIf { it > 0f }?.let {
            selector.width = it
          }
          workInfo.progress.getInt("maxChars", 0).takeIf { it > 0 }?.let {
            selector.maxChars = it
          }
          data.selector = selector
          workInfo.progress.getString("file")?.let {
            data.file = it
          }
          workInfo.progress.getString("jobId")?.let {
            data.jobId = it
          }
          workInfo.progress.getString("jobName")?.let {
            data.jobName = it
          }
          workInfo.progress.getString("jobTag")?.let {
            data.jobTag = it
          }
        }
        if (workInfo.outputData.keyValueMap.isNotEmpty()) {
          val selector = PrinterSelectorArgument()
          workInfo.outputData.getString("connection")?.let {
            selector.connection = it
          }
          workInfo.outputData.getString("address")?.let {
            selector.address = it
          }
          workInfo.outputData.getInt("port", 0).takeIf { it > 0 }?.let {
            selector.port = it
          }
          workInfo.outputData.getInt("baudrate", 0).takeIf { it > 0 }?.let {
            selector.baudrate = it
          }
          workInfo.outputData.getInt("dpi", 0).takeIf { it > 0 }?.let {
            selector.dpi = it
          }
          workInfo.outputData.getFloat("width", 0f).takeIf { it > 0f }?.let {
            selector.width = it
          }
          workInfo.outputData.getInt("maxChars", 0).takeIf { it > 0 }?.let {
            selector.maxChars = it
          }
          data.selector = selector
          workInfo.outputData.getString("file")?.let {
            data.file = it
          }
          workInfo.outputData.getString("jobId")?.let {
            data.jobId = it
          }
          workInfo.outputData.getString("jobName")?.let {
            data.jobName = it
          }
          workInfo.outputData.getString("jobTag")?.let {
            data.jobTag = it
          }
        }
        data.state = workInfo.state.name
        data.id = workInfo.id.toString()
        val tags = Arguments.createArray()
        workInfo.tags.forEach {
          tags.pushString(it)
        }
        data.tags = tags
        data.generation = workInfo.generation
        data.runAttemptCount = workInfo.runAttemptCount
      }
      when (workInfo.state) {
        WorkInfo.State.ENQUEUED -> {
        }

        WorkInfo.State.RUNNING -> {
        }

        WorkInfo.State.SUCCEEDED -> {
        }

        WorkInfo.State.FAILED -> {
          val errorMessage = workInfo.outputData.getString("error")
          data.error = errorMessage
        }

        WorkInfo.State.BLOCKED -> {
        }

        WorkInfo.State.CANCELLED -> {
        }
      }
      return data
    }
  }
}