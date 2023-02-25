package id.my.arieftb.soad.domain.common.model

/**
 * Request mapper
 *
 * is filled with mapping method to convert normal request object to be map
 *
 * @constructor Create empty Request mapper
 */
interface RequestMapper {
    /**
     * Body
     *
     * @constructor Create empty Body
     */
    interface Body {
        /**
         * To body
         *
         * @return
         */
        fun toBody(): Map<String, Any>
    }

    /**
     * Header
     *
     * @constructor Create empty Header
     */
    interface Header {
        /**
         * To header
         *
         * @return
         */
        fun toHeader(): Map<String, Any>
    }

    /**
     * Query
     *
     * @constructor Create empty Query
     */
    interface Query {
        /**
         * To query
         *
         * @return
         */
        fun toQuery(): Map<String, Any>
    }
}
