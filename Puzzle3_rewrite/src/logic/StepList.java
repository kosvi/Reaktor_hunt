package logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StepList {

	private List<Step> steps;

	public StepList() {
		this.steps = new ArrayList<>();
	}

	public void add(Step s) {
		// We don't want duplicates (coordinates) to our list,
		// but we want to make sure that we store the one with the smallest distance
		if (this.steps.contains(s)) {
			// ok, this coordinate already exists in our list
			int index = this.steps.indexOf(s);
			// replace Step at index with smaller of the current and s
			this.steps.set(index, Collections.min(Arrays.asList(s, this.steps.get(index))));
		} else {
			// ok, this coordinate doesn't exist in our list
			this.steps.add(s);
		}
	}

	public int size() {
		return this.steps.size();
	}

	public Step findStart() {
		return this.steps.get(0);
	}

	public boolean contains(Step s) {
		return this.steps.contains(s);
	}

	public int indexOf(Step s) {
		return this.steps.indexOf(s);
	}
}
