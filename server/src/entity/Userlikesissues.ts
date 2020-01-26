import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn, BaseEntity } from "typeorm";
import { Issues } from "./Issues";
import { Users } from "./Users";

@Index("USSERLIKESID_idx", ["user"], {})
@Index("ISSUELIKESID_idx", ["issue"], {})
@Entity("USERLIKESISSUES", { schema: "innodb" })
export class Userlikesissues extends BaseEntity {
  @PrimaryColumn("int", { name: "USER"})
  user: number | null;

  @PrimaryColumn("int", { name: "ISSUE"})
  issue: number | null;

  @ManyToOne(
    () => Issues,
    issues => issues.userlikesissues,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "ISSUE", referencedColumnName: "issueid" }])
  issue2: Issues;

  @ManyToOne(
    () => Users,
    users => users.userlikesissues,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "USER", referencedColumnName: "userid" }])
  user2: Users;
}
