package drift.com.drift.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.Date

/**
 * Created by eoin on 22/02/2018.
 */

internal class AppointmentInfo {

    @Expose
    @SerializedName("availabilitySlot")
    var availabilitySlot: Date? = null

    @Expose
    @SerializedName("agentId")
    var agentId: Long? = null

    @Expose
    @SerializedName("conversationId")
    var conversationId: Int? = null

    @Expose
    @SerializedName("slotDuration")
    var slotDuration: Int? = null

}
