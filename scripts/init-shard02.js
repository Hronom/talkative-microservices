rs.initiate(
   {
      _id: "shard02",
      members: [
         { _id: 0, host : "shard02a:27019" }
      ]
   }
)