package tv.mineinthebox.simpleserver.events.manager;

public enum EventPriority {
		LOW(0),
		MEDIUM(1),
		HIGH(2),
		HIGHEST(3);
		
		private final int priority;
		
		private EventPriority(int priority) {
			this.priority = priority;
		}
		
		public int getPriorityLevel() {
			return priority;
		}
}
