rs.initiate(
   {
      _id: "shard03",
      members: [
         { _id: 0, host : "shard03a:29019" }
      ]
   }
)