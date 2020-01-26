import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  OneToMany,
  PrimaryGeneratedColumn,
  BaseEntity
} from "typeorm";
import { Groups } from "./Groups";
import { Issues } from "./Issues";
import { Users } from "./Users";
import { Userlikessuggestions } from "./Userlikessuggestions";

@Index("USER_idx", ["user"], {})
@Index("ISSUE_idx", ["issue"], {})
@Index("GROUPSUGID_idx", ["group"], {})
@Entity("SUGGESTIONS", { schema: "innodb" })
export class Suggestions extends BaseEntity {
  @PrimaryGeneratedColumn({ type: "int", name: "SUGGESTIONSID" })
  suggestionsid: number;

  @Column("varchar", { name: "DATE", nullable: true, length: 45 })
  date: string | null;

  @Column("int", { name: "USER", nullable: true })
  user: number | null;

  @Column("varchar", { name: "TEXT", nullable: true, length: 500 })
  text: string | null;

  @Column("varchar", { name: "AREA", nullable: true, length: 45 })
  area: string | null;

  @Column("int", { name: "GROUP", nullable: true })
  group: number | null;

  @Column("int", { name: "LIKES", nullable: true })
  likes: number | null;

  @Column("int", { name: "ISSUE", nullable: true })
  issue: number | null;

  @ManyToOne(
    () => Groups,
    groups => groups.suggestions,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "GROUP", referencedColumnName: "groupid" }])
  group2: Groups;

  @ManyToOne(
    () => Issues,
    issues => issues.suggestions,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "ISSUE", referencedColumnName: "issueid" }])
  issue2: Issues;

  @ManyToOne(
    () => Users,
    users => users.suggestions,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "USER", referencedColumnName: "userid" }])
  user2: Users;

  @OneToMany(
    () => Userlikessuggestions,
    userlikessuggestions => userlikessuggestions.suggestion2
  )
  userlikessuggestions: Userlikessuggestions[];
}
