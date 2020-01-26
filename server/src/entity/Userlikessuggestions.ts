import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn, BaseEntity } from "typeorm";
import { Suggestions } from "./Suggestions";
import { Users } from "./Users";

@Index("USERLIKESSUG_idx", ["user"], {})
@Index("SUGGESTIONLIKED_idx", ["suggestion"], {})
@Entity("USERLIKESSUGGESTIONS", { schema: "innodb" })
export class Userlikessuggestions extends BaseEntity {
  @PrimaryColumn("int", { name: "USER"})
  user: number;

  @PrimaryColumn("int", { name: "SUGGESTION"})
  suggestion: number;

  @ManyToOne(
    () => Suggestions,
    suggestions => suggestions.userlikessuggestions,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "SUGGESTION", referencedColumnName: "suggestionsid" }])
  suggestion2: Suggestions;

  @ManyToOne(
    () => Users,
    users => users.userlikessuggestions,
    { onDelete: "NO ACTION", onUpdate: "NO ACTION" }
  )
  @JoinColumn([{ name: "USER", referencedColumnName: "userid" }])
  user2: Users;
}
