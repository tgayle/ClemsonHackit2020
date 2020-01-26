import { Column, Entity, Index, JoinColumn, ManyToOne, BaseEntity, PrimaryColumn } from "typeorm";
import { Groups } from "./Groups";
import { Users } from "./Users";

@Index("GROUPID_idx", ["groupid"], {})
@Index("USERID_idx", ["userid"], {})
@Entity("GROUPMEMBERS", { schema: "innodb" })
export class Groupmembers extends BaseEntity {
  @PrimaryColumn("int", { name: "GROUPID"})
  groupid: number | null;

  @PrimaryColumn("int", { name: "USERID"})
  userid: number | null;

  @ManyToOne(
    () => Groups,
    groups => groups.groupmembers,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "GROUPID", referencedColumnName: "groupid" }])
  group: Groups;

  @ManyToOne(
    () => Users,
    users => users.groupmembers,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "USERID", referencedColumnName: "userid" }])
  user: Users;
}
