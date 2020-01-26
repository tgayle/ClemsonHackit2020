import { Column, Entity, OneToMany, PrimaryGeneratedColumn, BaseEntity } from "typeorm";
import { Groupmembers } from "./Groupmembers";
import { Issues } from "./Issues";
import { Suggestions } from "./Suggestions";
import { Userlikesissues } from "./Userlikesissues";
import { Userlikessuggestions } from "./Userlikessuggestions";

@Entity("USERS", { schema: "innodb" })
export class Users extends BaseEntity {
  @Column("tinyint", { name: "VERIFIABLE", nullable: true })
  verifiable: number | null;

  @Column("varchar", { name: "FIRSTNAME", nullable: true, length: 45 })
  firstname: string | null;

  @Column("varchar", { name: "LASTNAME", nullable: true, length: 45 })
  lastname: string | null;

  @Column("varchar", { name: "TITLE", nullable: true, length: 45 })
  title: string | null;

  @Column("varchar", { name: "DOB", nullable: true, length: 45 })
  dob: string | null;

  @Column("varchar", { name: "STATE", nullable: true, length: 45 })
  state: string | null;

  @Column("varchar", { name: "CITY", nullable: true, length: 45 })
  city: string | null;

  @PrimaryGeneratedColumn({ type: "int", name: "USERID" })
  userid: number;

  @OneToMany(
    () => Groupmembers,
    groupmembers => groupmembers.user
  )
  groupmembers: Groupmembers[];

  @OneToMany(
    () => Issues,
    issues => issues.user2
  )
  issues: Issues[];

  @OneToMany(
    () => Suggestions,
    suggestions => suggestions.user2
  )
  suggestions: Suggestions[];

  @OneToMany(
    () => Userlikesissues,
    userlikesissues => userlikesissues.user2
  )
  userlikesissues: Userlikesissues[];

  @OneToMany(
    () => Userlikessuggestions,
    userlikessuggestions => userlikessuggestions.user2
  )
  userlikessuggestions: Userlikessuggestions[];
}
